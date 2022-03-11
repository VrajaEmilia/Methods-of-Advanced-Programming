package Controller;

import Domain.PrgState;
import Domain.Value.RefValue;
import Domain.Value.Value;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GarbageCollector {
    static Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value>
            heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
       }
    static Set<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Collection<Value> heap){
        return Stream.concat(symTableValues.stream(),heap.stream()).filter(
                value->value instanceof RefValue
        ).map(value->{
            RefValue value1=(RefValue)value;
            return value1.getAddr();
        }).collect(Collectors.toSet());
    }
    public static void conservativeGarbageCollector(List<PrgState> prgStates) {
        var heap=Objects.requireNonNull(Objects.requireNonNull(prgStates.stream().map(
                prg -> getAddrFromSymTable(prg.getSymbolTable().getElems().values(), prg.getHeap().getHeap().values())
        ).map(Collection::stream).reduce(Stream::concat).orElse(null)).collect(
                Collectors.toList()
        ));
        prgStates.forEach(prg->{
            prg.getHeap().setHeap(
                    (HashMap<Integer, Value>) unsafeGarbageCollector(heap,prgStates.get(0).getHeap().getHeap())
            );
        });
    }
}
