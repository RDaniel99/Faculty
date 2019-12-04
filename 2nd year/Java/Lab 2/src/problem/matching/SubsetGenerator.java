package problem.matching;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Generate the set of all subsets of a Set.
 */
public class SubsetGenerator {

    /**
     * compute all of the sets that are a subset of the Set given as a parameter.
     * @param originalSet
     * The Set for which we want to compute all subsets.
     * @return
     * Set of Sets, each representing a subset of the Set given as a parameter.
     */
    public Set <Set<String>> makeSetOfSubsets(Set <String> originalSet){
        Set <Set<String>> resultingSet=new HashSet<>();
        if (originalSet.isEmpty()){
            resultingSet.add(new HashSet<>());
            return resultingSet;
        }
        List <String> originalSetAsList=new ArrayList<>(originalSet);
        String headOfList = originalSetAsList.get(0);
        Set <String> rest = new HashSet<>(originalSetAsList.subList(1,originalSetAsList.size()));
        for (Set<String> subset:makeSetOfSubsets(rest)){
            Set <String> newSubset = new HashSet<>();
            newSubset.add(headOfList);
            newSubset.addAll(subset);
            resultingSet.add(newSubset);
            resultingSet.add(subset);
        }
        return resultingSet;
    }
}
