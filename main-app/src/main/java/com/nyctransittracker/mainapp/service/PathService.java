package com.nyctransittracker.mainapp.service;

import com.nyctransittracker.mainapp.model.Path;
import com.nyctransittracker.mainapp.repository.PathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PathService {

    private final PathRepository pathRepository;

    public Path savePath(Path path) {
        return pathRepository.save(path);
    }

    public void saveAllPaths(List<Path> paths) {
        pathRepository.saveAll(paths);
    }

    public Optional<Path> getPath(String pathName) {
        return pathRepository.findByPathName(pathName);
    }

    public boolean isEmpty() {
        return pathRepository.count() > 0;
    }
}
