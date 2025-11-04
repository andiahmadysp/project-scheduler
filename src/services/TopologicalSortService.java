package services;

import enums.VisitState;
import java.util.*;

// service untuk melakukan topological sort
public class TopologicalSortService {

    // melakukan topological sort pada adjacency list
    public List<String> sort(Map<String, List<String>> adjacencyList, Set<String> allTasks) {
        Map<String, VisitState> visitState = new HashMap<>();
        Stack<String> stack = new Stack<>();

        // inisialisasi semua task sebagai unvisited
        for (String task : allTasks) {
            visitState.put(task, VisitState.UNVISITED);
        }

        // lakukan dfs untuk setiap task yang belum dikunjungi
        for (String task : allTasks) {
            if (visitState.get(task) == VisitState.UNVISITED) {
                if (hasCycle(task, adjacencyList, visitState, stack)) {
                    return new ArrayList<>();
                }
            }
        }

        return convertStackToList(stack);
    }

    // melakukan dfs untuk deteksi cycle dan build topological order
    private boolean hasCycle(String task, Map<String, List<String>> adjacencyList,
                             Map<String, VisitState> visitState, Stack<String> stack) {
        visitState.put(task, VisitState.VISITING);

        List<String> dependencies = adjacencyList.get(task);
        if (dependencies != null) {
            for (String dependent : dependencies) {
                VisitState state = visitState.get(dependent);

                if (state == VisitState.VISITING) {
                    return true;
                }

                if (state == VisitState.UNVISITED) {
                    if (hasCycle(dependent, adjacencyList, visitState, stack)) {
                        return true;
                    }
                }
            }
        }

        visitState.put(task, VisitState.VISITED);
        stack.push(task);

        return false;
    }

    // konversi stack ke list dalam urutan terbalik
    private List<String> convertStackToList(Stack<String> stack) {
        List<String> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }
}
