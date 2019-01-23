// Accepts strings of the format a*b*
states 3
s0
s1
s2 +
// Alphabet
alphabet 2 a b
// input 'a' loops in s0
s0 a s0 a R
// input 'b' loops in s1
s0 b s1 b R
s1 b s1 b R
// underscore is end of input
s0 _ s2 _ S
s1 _ s2 _ S