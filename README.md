# xword-puzzle

My friend had an interview question.
This one is about crosswords. It involves graphs obviously.
Let me start by giving a brief overview of the problem he had to solve, and my Scala take on it ;-)
Question Imagine a crossword grid of size n x n containing letters. Given a dictionary of words, find all possible paths in the grid that match the words in the dictionary.

For Example: 
MBC
ARA
NMO

is a 3x3 grid of letters, if the dictionary contains the word MAN, there are two paths possible: [(0,0), (0,1), (0,2)] and [(1,2), (0,1), (0,2)]. 