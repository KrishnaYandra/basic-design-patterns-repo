package com.java.design.patterns.behavioral.iterator.datasource.implementation;

import com.java.design.patterns.behavioral.iterator.datasource.DataSource;
import com.java.design.patterns.behavioral.iterator.iterator.DataIterator;
import com.java.design.patterns.behavioral.iterator.model.DataRecord;
import com.java.design.patterns.behavioral.iterator.predicates.FilterPredicate;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class FileSystemDataSource implements DataSource {
    private Path rootDirectory;
    private String fileExtension;

    public FileSystemDataSource(Path rootDirectory, String fileExtension) {
        this.rootDirectory = rootDirectory;
        this.fileExtension = fileExtension;
    }

    @Override
    public DataIterator createIterator() {
        return new FileSystemIterator(rootDirectory, fileExtension, null);
    }

    @Override
    public DataIterator createFilteredIterator(FilterPredicate predicate) {
        return new FileSystemIterator(rootDirectory, fileExtension, predicate);
    }

    @Override
    public int getTotalCount() {
        try (Stream<Path> paths = Files.walk(rootDirectory)) {
            return (int) paths.filter(Files::isRegularFile)
                             .filter(p -> p.toString().endsWith(fileExtension))
                             .count();
        } catch (IOException e) {
            return 0;
        }
    }

    @Override
    public String getSourceType() {
        return "FILESYSTEM";
    }

    private class FileSystemIterator implements DataIterator {
        private List<Path> allFiles;
        private int currentIndex;
        private FilterPredicate filter;

        public FileSystemIterator(Path root, String extension, FilterPredicate filter) {
            this.filter = filter;
            this.currentIndex = 0;
            this.allFiles = new ArrayList<>();
            loadFiles(root, extension);
        }

        private void loadFiles(Path root, String extension) {
            try (Stream<Path> paths = Files.walk(root)) {
                allFiles = paths.filter(Files::isRegularFile)
                               .filter(p -> p.toString().endsWith(extension))
                               .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean hasNext() {
            while (currentIndex < allFiles.size()) {
                if (filter == null) {
                    return true;
                }
                
                // Pre-check if next record passes filter
                try {
                    DataRecord record = createRecordFromFile(allFiles.get(currentIndex));
                    if (filter.test(record)) {
                        return true;
                    }
                    currentIndex++;
                } catch (IOException e) {
                    currentIndex++;
                }
            }
            return false;
        }

        @Override
        public DataRecord next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more files");
            }

            try {
                Path file = allFiles.get(currentIndex++);
                return createRecordFromFile(file);
            } catch (IOException e) {
                throw new RuntimeException("Error reading file", e);
            }
        }

        private DataRecord createRecordFromFile(Path file) throws IOException {
            String content = Files.readString(file);
            long timestamp = Files.getLastModifiedTime(file).toMillis();
            
            DataRecord record = new DataRecord(
                file.getFileName().toString(),
                "FILESYSTEM",
                content,
                timestamp
            );
            record.addMetadata("path", file.toString());
            record.addMetadata("size", Files.size(file));
            
            return record;
        }

        @Override
        public void reset() {
            currentIndex = 0;
        }

        @Override
        public int getCurrentPosition() {
            return currentIndex;
        }

        @Override
        public boolean hasPrevious() {
            return currentIndex > 0;
        }

        @Override
        public DataRecord previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException("No previous element");
            }
            try {
                Path file = allFiles.get(--currentIndex);
                return createRecordFromFile(file);
            } catch (IOException e) {
                throw new RuntimeException("Error reading file", e);
            }
        }
    }
}
