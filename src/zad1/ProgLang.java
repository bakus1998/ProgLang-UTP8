package zad1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProgLang<T,S> {
    String path;
    BufferedReader reader;
    FileInputStream fis;
    Map<T, LinkedHashSet> lMap;
    Map<T, LinkedHashSet> pMap;
    Comparator<Map.Entry<T, LinkedHashSet>> comparator = new Comparator<Map.Entry<T, LinkedHashSet>>() {
        @Override
        public int compare(Map.Entry<T, LinkedHashSet> o1, Map.Entry<T, LinkedHashSet> o2) {
            if(o1.getValue().size()>o2.getValue().size()){
                return -1;
            }else if(o1.getValue().size()<o2.getValue().size()){
                return 1;
            }else if(o1.getValue().size()==o2.getValue().size()){
                return o1.getKey().toString().compareTo(o2.getKey().toString());
            }
            return Integer.compare(o2.getValue().size(),o2.getValue().size());
        }
    };


    public ProgLang(String path) {
        this.path=path;
        String[] readWord;
        String[] readWord2;
        lMap = new LinkedHashMap<>();
        pMap = new LinkedHashMap<>();

        try {
            fis = new FileInputStream(new File(path));
            reader = new BufferedReader(new InputStreamReader(fis));
            String s_reader;
            while ((s_reader = reader.readLine()) != null) {
                readWord = s_reader.split("\t");
                if (!lMap.containsKey(readWord[0])) {
                    LinkedHashSet set = new LinkedHashSet();
                    for (int i = 1; i < readWord.length; i++) {
                        set.add(readWord[i]);
                    }
                    lMap.put((T) readWord[0], set);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            FileInputStream fis2 = new FileInputStream(new File(path));
            reader = new BufferedReader(new InputStreamReader(fis2));
            String s_reader;
            while ((s_reader = reader.readLine()) != null) {
                readWord2 = s_reader.split("\t");
                for (int i = 1; i < readWord2.length; i++) {
                    if(!pMap.containsKey(readWord2[i])){
                        LinkedHashSet set = new LinkedHashSet();
                        set.add(readWord2[0]);
                        pMap.put((T)readWord2[i],set);
                    }else{
                        pMap.get(readWord2[i]).add(readWord2[0]);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public Map<T, LinkedHashSet> getLangsMap() {
        return lMap;
    }

    public Map<T, LinkedHashSet> getProgsMap() {
        return pMap;
    }

    public Map<T, S> getLangsMapSortedByNumOfProgs() {
        Map<T,S> sortedMap = (LinkedHashMap<T, S>) lMap.entrySet().stream().sorted(comparator)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (e1, e2) -> e1, LinkedHashMap::new));;

    return sortedMap;
    }


    public Map<T, S> getProgsMapSortedByNumOfLangs() {
        Map<T,S> SortedMap = (LinkedHashMap<T, S>) pMap.entrySet().stream().sorted(
        comparator).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (e1, e2) -> e1, LinkedHashMap::new));;
        return SortedMap;
    }

    public Map<T, S> getProgsMapForNumOfLangsGreaterThan(int i) {
        Map<T, S> map = (LinkedHashMap<T, S>) pMap.entrySet()
                .stream()
                .filter(e -> e.getValue().size() > i)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return map;
    }
}
