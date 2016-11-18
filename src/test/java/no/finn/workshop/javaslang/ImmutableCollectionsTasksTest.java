package no.finn.workshop.javaslang;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javaslang.Function1;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.HashMap;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import javaslang.collection.TreeSet;
import javaslang.collection.Vector;
import no.finn.workshop.javaslang.things.Age;
import org.junit.Test;

import static no.finn.workshop.javaslang.LambdaAssert.assertEquality;
import static no.finn.workshop.javaslang.LambdaAssert.assertTrue;

public class ImmutableCollectionsTasksTest {

    @Test
    public void should_create_javaslang_list_from_list() {
        java.util.List<Integer> rawList = Arrays.asList(1, 2, 3, 4);
        List<Integer> result = ImmutableCollectionsTasks.createFrom(rawList);
        assertTrue(result, l -> l != null && l.nonEmpty(), "createFrom should return a nonempty javaslang List");
        assertTrue(result, l -> l.equals(List.of(1, 2, 3, 4)), "createFrom should return a javaslang List containing 1,2,3,4");
        assertEquality(
                ImmutableCollectionsTasks.createFrom(rawList),
                List.of(1,2,3,4),
                Object::equals,
                "createFrom should return a javaslang List containing 1,2,3,4"
        );
    }


    @Test
    public void should_create_javaslang_list_from_varargs_object_array() {
        java.util.List<Integer> rawList = Arrays.asList(1, 2, 3, 4);
        List<Integer> result = ImmutableCollectionsTasks.createFromAll(1,2,3,4);
        assertTrue(result, l -> l != null && l.nonEmpty(), "createFrom should return a nonempty javaslang List");
        assertTrue(result, l -> l.equals(List.of(1, 2, 3, 4)), "createFrom should return a javaslang List containing 1,2,3,4");
        assertEquality(
                ImmutableCollectionsTasks.createFrom(rawList),
                List.of(1,2,3,4),
                Object::equals,
                "createFrom should return a javaslang List containing 1,2,3,4"
        );
    }

    @Test
    public void should_map_all_integers_to_age_in_a_list() {
        List<Integer> rawList = List.of(18, 20, 37);
        List<Age> result = ImmutableCollectionsTasks.transfomElements(rawList);
        assertTrue(result, l -> l != null && l.nonEmpty(), "transformElements should create a List<Age> from List<Integer>");
        assertEquality(
                ImmutableCollectionsTasks.transfomElements(rawList),
                List.of(new Age(18), new Age(20), new Age(37)),
                Object::equals,
                "transformElements should create List<Age> where each Age match the original Integer"
        );
    }

    @Test
    public void should_check_that_list_of_strings_with_different_caseing_corresponds() {
        List<String> first = List.of("a", "b", "C", "D");
        List<String> second = List.of("A", "B", "c", "d");
        assertTrue(ImmutableCollectionsTasks.checkSameStringsIgnoreCase(first, second), b -> b,
                "checkSameStringsIgnoreCase should return true when two lists contain equal Strings whith casing ignored");
    }

    @Test
    public void should_double_every_int_in_list() {
        List<Integer> ints = List.of(1, 2, 3);
        assertTrue(ImmutableCollectionsTasks.doubleEveryInt(ints), List.of(2, 4, 6)::equals, "Expected List(1,2,3) to become List(2,4,6) after doubling");
    }

    @Test
    public void should_retain_odd_ints_in_list() {
        List<Integer> ints = List.of(1,4,9,12);
        assertTrue(ImmutableCollectionsTasks.retainOddNumbers(ints), List.of(1, 9)::equals, "Expected to retain List(1,9) from List.of(1,4,9.12)");
    }

    @Test
    public void should_create_tuple_from_string_and_int() {
        Tuple2<String, Integer> yo = ImmutableCollectionsTasks.createTupleFrom("yo", 5);
        assertEquality(new Tuple2<>("yo", 5), yo, Tuple2::equals, "createTupleFrom should create a Tuple2<String, Integer> containing the parameters of the method");
    }

    @Test
    public void should_flip_every_other_tuple_in_a_list_of_tuple2() {
        List<Tuple2<Integer, Integer>> list = List.of(Tuple.of(1, 2), Tuple.of(3, 1), Tuple.of(1, 9), Tuple.of(0, -1));
        List<Tuple2<Integer, Integer>> result = ImmutableCollectionsTasks.flipEveryOther(list);

        List<Tuple2<Integer, Integer>> expected = List.of(Tuple.of(2, 1), Tuple.of(3, 1), Tuple.of(9, 1), Tuple.of(0, -1));
        assertEquality(expected, result, List::equals, "flipEveryOther should flip every other Tuple2<Integer, Integer> (starting from the first) in a List");
    }

    @Test
    public void should_add_ten_to_integer_element_of_tuple() {
        Tuple2<String, Integer> transformed = ImmutableCollectionsTasks.transformTuple(Tuple.of("yo", 4));
        assertEquality(Tuple.of("yo", 14), transformed, Tuple2::equals, "transformTuple should add 10 to second element, and keep first element unaltered");
    }

    @Test
    public void should_create_javaslang_map_from_java_util_map() {
        java.util.Map<String, Integer> map = new java.util.HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        javaslang.collection.HashMap<String, Integer> javaslangMap =
                ImmutableCollectionsTasks.toJavaslangMap(map);
        assertTrue(javaslangMap, l -> l.equals(javaslang.collection.HashMap.of("a", 1, "b", 2)), "createFrom should return a javaslang HashMap containing a->1,b->2");
    }

    @Test
    public void should_create_javaslang_HashSet_from_java_util_HashSet(){
        java.util.Set<String> set = new java.util.HashSet<>();
        set.add("do");
        set.add("re");
        set.add("mi");
        javaslang.collection.HashSet<String> strings = ImmutableCollectionsTasks.toJavaslangSet(set);
        assertTrue(strings, s -> s.equals(javaslang.collection.HashSet.of("do", "re", "mi")), "should create a javaslang HashSet from java util HashSet with strings do re mi");
    }

    @Test
    public void should_create_java_list_from_javaslang_list() {
        List<String> list = List.of("do", "re", "mi");
        java.util.List<String> strings = ImmutableCollectionsTasks.toJavaList(list);
        assertTrue(strings, Arrays.asList("do", "re", "mi")::equals, "expected java.util.List of strings do re mi");

    }

    @Test
    public void should_create_a_javaslang_stream_from_given_range() {
        javaslang.collection.Stream<Integer> stream = ImmutableCollectionsTasks.createStream(0, 3);
        assertTrue(stream.toList(), javaslang.collection.Stream.of(0, 1, 2, 3)::eq, "expected stream of 0 1 2 3");
    }

    @Test
    public void should_create_a_javaslang_list_from_java_util_stream() {
        List<String> list = ImmutableCollectionsTasks.toJavaslangList(Stream.of("a", "b", "c"));
        assertTrue(list, List.of("a", "b", "c")::eq, "expected javaslang list of a b c");
    }

    @Test
    public void should_create_a_function_from_val_to_bool_for_javaslang_hashset() {
        Function1<Integer, Boolean> integerBooleanFunction1 = ImmutableCollectionsTasks.toFunction(HashSet.of(1, 2, 9));
        assertEquality(true, integerBooleanFunction1.apply(1), Boolean::equals, "expected Set containing val 1 to return true for funciton applied to 1");
        assertEquality(false, integerBooleanFunction1.apply(4), Boolean::equals, "expected Set containing val 4 to return false for function applied to 4");
    }

    @Test
    public void should_create_a_function_from_key_to_val_for_javaslang_hashmap() {
        HashMap<String, Integer> of = HashMap.of("a", 4, "b", 5);

        Function1<String, Integer> stringIntegerFunction1 = ImmutableCollectionsTasks.toFunction(of);
        assertEquality(4, stringIntegerFunction1.apply("a"), Integer::equals, "expected 4 as result of applying string a for a map containing a -> 4");
    }

    @Test
    public void should_sum_integers_in_a_set() {
        Integer sum = ImmutableCollectionsTasks.sum(HashSet.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        assertEquality(55, sum, Integer::equals, "Expected 55 as the sum of 1 .. 10");
    }

    @Test
    public void should_transform_set_to_list() {
        List<Integer> integers = ImmutableCollectionsTasks.toList(HashSet.of(1, 2, 3));
        assertEquality(List.of(1, 2, 3), integers, List::equals, "Expected List(1,2,3) from Set(1,2,3)");
    }

    @Test
    public void should_create_vector_of_doubles() {
        Vector<Double> doubles = ImmutableCollectionsTasks.toVector(0.0, 1.0, 5.0);
        assertEquality(Vector.of(0.0, 1.0, 5.0), doubles, Vector::equals, "Expected Vector(0.0, 1.0, 5.0)");
    }

    @Test
    public void should_create_the_alfabet() {
        Array<Character> alfabet = ImmutableCollectionsTasks.alfabet();
        assertEquality(Array.of('a', 'b'), alfabet.take(2), Array::equals, "Expected the first two Characters to be Array('a', 'b')");
    }

    @Test
    public void should_sort_characters_using_a_treeset() {
        List<Character> sorted = ImmutableCollectionsTasks.sort('z', 't', 'a', 'k', 'o');
        assertEquality(List.of('a', 'k', 'o', 't', 'z'), sorted, List::equals, "Expected List('a', 'k, 'o', 't', 'z')");
    }

    @Test
    public void should_find_the_intersection_between_two_trees() {
        TreeSet<Integer> first = TreeSet.of(1, 2, 3);
        TreeSet<Integer> second = TreeSet.of(2, 3, 4);
        TreeSet<Integer> intersection = ImmutableCollectionsTasks.intersection(first, second);
        assertEquality(TreeSet.of(2, 3), intersection, TreeSet::equals, "Expected intersection of (1,2,3) and (2,3,4) to be (2,3)");
    }

    @Test
    public void should_split_a_tree_using_a_predicate() {
        TreeSet<Integer> set = TreeSet.rangeClosed(1, 10);
        Predicate<Integer> predicate = i -> i % 2 == 0;
        Tuple2<TreeSet<Integer>, TreeSet<Integer>> partitioned = ImmutableCollectionsTasks.split(set, predicate);
        assertEquality(TreeSet.of(2, 4, 6, 8, 10), partitioned._1, TreeSet::equals, "expected first in partitioned tuple to be (2,4,6,8,10) given " + set + " with even-number predicate");
        assertEquality(TreeSet.of(1, 3, 5, 7, 9), partitioned._2, TreeSet::equals, "expected second in partitioned tuple to be (1,3,5,7,9) given " + set + " and odd-number predicate");
    }

}
