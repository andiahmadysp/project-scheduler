package core;

import services.TopologicalSortService;

import java.util.*;

// class untuk mengelola penjadwalan tugas proyek menggunakan topological sort
public class ProjectScheduler {
    private final Map<String, List<String>> adjacencyList;
    private final Set<String> allTasks;
    private final TopologicalSortService sortService;

    public ProjectScheduler() {
        this.adjacencyList = new HashMap<>();
        this.allTasks = new HashSet<>();
        this.sortService = new TopologicalSortService();
    }

    // menambahkan tugas baru ke dalam proyek
    public void addTask(String task) {
        allTasks.add(task);
        adjacencyList.putIfAbsent(task, new ArrayList<>());
    }

    // menambahkan dependensi antar tugas
    public void addDependency(String prerequisiteTask, String dependentTask) {
        adjacencyList.get(prerequisiteTask).add(dependentTask);
    }

    // mendapatkan urutan pengerjaan tugas yang valid
    public List<String> getTaskOrder() {
        return sortService.sort(adjacencyList, allTasks);
    }
}
