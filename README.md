Using a graph, this project parses a file that contains sorted words 
regardless of the symbol used. For example, a file such as 

#$%!
 
&%@^(*

^&*(

@#%&!@

^*&(

...

can be parsed to determine an "alphabet" with the assumption that
the input file contains "words" in "alphabetical" order. It constructs
an alphabet using the given input file, and the alphabet can be used to
sort further files not in alphabetical order but using the same character set.

