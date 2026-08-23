package src.main.java.sort.typeOfSort;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class SelectionSort<T> implements SortStrategy<T> {
    @Override
    public void sort(List<T> list, Comparator<? super T> comparator) {
        Objects.requireNonNull(list, "Список не может быть null");
        Objects.requireNonNull(comparator, "Компаратор не может быть null");

        for(int i =0;i<list.size()-1;i++){
            int minInd = i;

            for(int j=i+1;j<list.size();j++){
                if(comparator.compare(list.get(j),list.get(minInd))<0){
                    minInd=j;
                }
            }
            if(minInd !=i){
                Swap.swap(list,i,minInd);
            }
        }
    }

}
