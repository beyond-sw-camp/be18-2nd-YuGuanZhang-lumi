package com.yuguanzhang.lumi.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Path;

@Getter
@AllArgsConstructor
public class FilePathInfo {
    private final Path fullPath;
    private final String relativePath;
}
