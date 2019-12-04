package problem.matching;

import problem.Problem;
import problem.projects.Project;
import problem.students.Student;

import java.util.*;

/**
 * The Graph class handles a bipartite graph and can perform verification of Hall's Theorem and can compute the maximum matching in it
 */
public class Graph {

    private Map<String, List<String>> adjacencyList;

    /**
     * Default constructor.
     */
    public Graph(){
        adjacencyList=new HashMap<>();
    }

    /**
     * Takes a Problem instance and computes a adjacency list for the graph containing the students and the projects
     * in the problem, with the students names and project names being the nodes and the edges between students and their choices
     * of projects.
     * @param problem
     * An instance of a valid Problem
     */
    private void makeAdjacencyLists(Problem problem) {
        if (!adjacencyList.isEmpty()) return;
        for (Student student:problem.getStudents()){
            List<String> adjacencyListForCurrentNode=new ArrayList<>();
            for (Project project:student.getPreferences()){
                adjacencyListForCurrentNode.add(project.getName());
            }
            adjacencyList.put(student.getName(),adjacencyListForCurrentNode);
        }
        for (Project project:problem.getProjects()){
            List <String> adjacencyListForCurrentNode=new ArrayList<>();
            for (Student student:problem.getStudents()){
                if (student.getPreferences().contains(project)){
                    adjacencyListForCurrentNode.add(student.getName());
                }
            }
            adjacencyList.put(project.getName(),adjacencyListForCurrentNode);
        }
    }

    /**
     * Returns true if Hall's Theorem is satisfied for a Problem instance.
     * @param problem
     * A valid instance of Problem
     * @return
     * True, if Hall's Theorem is satisfied. False, otherwise.
     */
    public boolean hallTheorem(Problem problem){
        makeAdjacencyLists(problem);
        SubsetGenerator subsets=new SubsetGenerator();
        Set <String> studentNames=new HashSet<>();
        for (Student student:problem.getStudents()){
            studentNames.add(student.getName());
        }
        for (Set <String> subset:subsets.makeSetOfSubsets(studentNames)){
            Set<String> projectsSetForSubsetOfStudents = new HashSet<>();
            for (String studentName:subset){
                projectsSetForSubsetOfStudents.addAll(adjacencyList.get(studentName));
            }
            if (projectsSetForSubsetOfStudents.size()<subset.size()) return false;
        }
        return true;
    }


    /**
     * Returns a maximum matching for a given Problem instance. The matching is returned as a Map.
     * @param problem
     * A valid instance of Problem
     * @return
     * A Map containing the matching between students and projects or vice-versa.
     */
    public Map <String,String> hopcroftKarp(Problem problem){
        makeAdjacencyLists(problem);
        Set <String> studentNames=new HashSet<>();
        for (Student student:problem.getStudents()){
            studentNames.add(student.getName());
        }
        Map <String, String> matching = new HashMap<>();
        boolean ok=true;
        int matchings=0;
        while (ok) {
            ok = false;
            Set<String> visitedNodes = new HashSet<>();
            for (String studentName : studentNames) {
                if (!matching.containsKey(studentName)) {
                    if (match(studentName, visitedNodes, matching)) {
                        ok = true;
                        matchings++;
                    }
                }
            }
        }
        return matching;
    }

    /**
     * A DFS that makes an augmenting path from a free node (not matched) to another free node
     * @param nodeToMatch
     * Node to start from
     * @param visitedNodes
     * A Set containing nodes that are already visited.
     * @param matching
     * A Map containing current matching
     * @return
     * Returns true, if an augmenting path can be formed from nodeToMatch and false, otherwise.
     */
    private boolean match(String nodeToMatch, Set <String> visitedNodes, Map <String, String> matching){
        if (visitedNodes.contains(nodeToMatch)) return false;
        visitedNodes.add(nodeToMatch);
        for (String neighbour:adjacencyList.get(nodeToMatch)){
            if (!matching.containsKey(neighbour)){
                matching.put(nodeToMatch, neighbour);
                matching.put(neighbour, nodeToMatch);
                return true;
            }
        }
        for (String neighbour:adjacencyList.get(nodeToMatch)){
            if (match(matching.get(neighbour),visitedNodes,matching)){
                matching.put(nodeToMatch,neighbour);
                matching.put(neighbour,nodeToMatch);
                return true;
            }
        }
        return false;
    }

}
