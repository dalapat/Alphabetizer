The problem we attempted to solve in Task 2 is: given a list of sorted "words" whose 
characters are symbols, we want to create an ordering of sorts defined by this list such 
that this ordering could be used to sort a separate unsorted list. In order to solve this
problem, we decided to use an adjacency matrix implementation of a graph. We chose a graph
data structure because the graph is useful in scenarios where we would like to define a
linear sequence given an unordered sequence. We chose an adjacency matrix because we assumed
that for larger text files and as the number of distinct characters in the dictionary grow, 
the graph becomes more dense, which makes the alternate adjacency list implementation
approach O(v^2) anyway, which is the same as the adjacency matrix. 

Our overall algorithm was to store both the dictionary and the unordered list of words
in 2 separate collections, use the dictionary collection to form an adjacency matrix, 
perform a topological sort on the adjacency matrix to output an "alphabet" or a correct 
ordering of the distinct characters. We would then use this alphabet in order to perform a 
bubble sort on the unsorted collection, where the swap/compare method would use this alphabet
in order to perform comparisons. We decided to make the alphabet a String, and all we needed to
do therefore when comparing two Strings during a swap in the bubble sort is: find the first 
instance of two characters in the two separate Strings that are different, check which character
comes first in the alphabet, and choose which word should come first because of it. 

In order to form the alphabet, we wrote a method (produceAlphabet) that would receive an unordered
String of the distinct characters in the dictionary file as well as the adjacency matrix. We then
perform a topological sort on the adjacency matrix. We find vertices that have no incoming edges
(indegree = 0) and keep an "added" count. We "disconnect" this vertex in the sense that we remove
it from the set of all nodes with incoming edges. This vertex is a character and we dynamically
build a String which will have the characters in sorted order. In other words, we find vertices
that have no incoming edges and we add them to a "set" which in this case is the String. If the
node that the vertex points to has no other incoming edges other than the current, we add it to
the set and remove the edge. Using this algorithm, we can create a sorted alphabet. 

 
