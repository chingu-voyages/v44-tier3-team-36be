package com.nyctransittracker.mainapp.service;

import com.nyctransittracker.mainapp.model.Path;
import com.nyctransittracker.mainapp.repository.PathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PathService {

    private final PathRepository pathRepository;

    public void savePath(Path path) {
        pathRepository.save(path);
    }

    public void saveAllPaths(List<Path> paths) {
        pathRepository.saveAll(paths);
    }

    public Path getPath(String pathName) {
        return pathRepository.findByPathName(pathName).orElseThrow();
    }
}
