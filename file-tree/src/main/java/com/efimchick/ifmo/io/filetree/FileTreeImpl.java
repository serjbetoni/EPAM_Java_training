package com.efimchick.ifmo.io.filetree;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FileTreeImpl implements FileTree {
    private static final String EMPTY_STRING = "";
    private static final String SPACE = " ";
    private static final String INDENT = "  ";
    private static final String VERTICAL_BRANCH = "│";
    private static final String CLOSING_BRANCH = "└";
    private static final String BRANCH = "─ ";
    private static final String OPENING_BRANCH = "├";
    private static final String BYTES = "bytes";

    @Override
    public Optional<String> tree(Path path) {
        if (path == null || !path.toFile().exists()) {
            return Optional.empty();
        }
        File file = path.toFile();
        if (file.isFile()) {
            return Optional.of(getFileName(file));
        }
        return Optional.of(getTree(file, EMPTY_STRING).getTree());
    }

    private String getFileName(File file) {
        String fileName = file.getName();
        long fileSize = file.length();
        return getName(fileName, fileSize);
    }

    private String getName(String name, long size) {
        return name + SPACE + size + SPACE + BYTES;
    }

    private TreeResult getTree(File directory, String indent) {
        List<File> files = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
        files.sort((File a, File b) -> {
            int result = Boolean.compare(a.isFile(), b.isFile());
            if (result == 0) {
                result = a.getName().compareToIgnoreCase(b.getName());
            }
            return result;
        });

        long size = 0;
        StringBuilder treeStringBuilder = new StringBuilder();

        for (File file : files) {
            treeStringBuilder.append("\n");
            treeStringBuilder.append(indent);

            StringBuilder indentStringBuilder = new StringBuilder(indent);
            boolean isLast = file.equals(files.get(files.size() - 1));
            indentStringBuilder.append(isLast ? SPACE
                    : VERTICAL_BRANCH);

            indentStringBuilder.append(INDENT);

            treeStringBuilder.append(isLast ? CLOSING_BRANCH
                    : OPENING_BRANCH);

            treeStringBuilder.append(BRANCH);

            if (file.isFile()) {
                size += file.length();
                treeStringBuilder.append(getFileName(file));
            } else {
                TreeResult treeResult = getTree(file, indentStringBuilder.toString());
                size += treeResult.getSize();
                treeStringBuilder.append(treeResult.getTree());
            }
        }
        String name = directory.getName();
        return new TreeResult(getName(name, size) + treeStringBuilder, size);
    }
}
