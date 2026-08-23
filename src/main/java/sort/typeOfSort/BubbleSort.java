package src.main.java.sort.typeOfSort;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class BubbleSort<T> implements SortStrategy<T>{
    @Override
    public void sort(List<T> list, Comparator<? super T> comparator) {
        Objects.requireNonNull(list,"Список не должен быть null");
        Objects.requireNonNull(comparator,"Компаратор не должен быть null");

        for(int i =0;i<list.size() -1;i++){
            boolean swaped = false;
            for(int j =0;j<list.size()-1;j++){
                if(comparator
                        .compare(list.get(j), list.get(j+1))>0)
                {
                    Swap.swap(list,j,j+1);
                    swaped = true;
                }
            }
            if(!swaped){
                return;
            }
        }
    }
}
