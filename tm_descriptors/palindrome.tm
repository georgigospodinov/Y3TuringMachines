// Loops back and forth, removing matching characters.
states 9
// Reading state reads one character and replaces it with '_'.
Reading
// Skipping states are used to skip all the numbers between the first and last.
Skipping0
Skipping1
Skipping2
// Expecting states are used to denote what character is expected at end of skipping.
Expecting0
Expecting1
Expecting2
// Reversing state causes the TM to go back to the start (index of first '_').
Reversing
Confirmation +
// Alphabet
alphabet 3 0 1 2
//
//
// Reading a symbol puts the TM in the appropriate skipping state.
Reading 0 Skipping0 _ R
Reading 1 Skipping1 _ R
Reading 2 Skipping2 _ R
// If there is no symbol to be read, then
// all pairs of symbols have been replaces with '_'.
// Which means that the input was indeed a palindrome.
Reading _ Confirmation _ R
// Skip characters until the end of the tape is reached.
Skipping0 0 Skipping0 0 R
Skipping0 1 Skipping0 1 R
Skipping0 2 Skipping0 2 R
Skipping1 0 Skipping1 0 R
Skipping1 1 Skipping1 1 R
Skipping1 2 Skipping1 2 R
Skipping2 0 Skipping2 0 R
Skipping2 1 Skipping2 1 R
Skipping2 2 Skipping2 2 R
// After reaching the end of the tape
// switch to the appropriate expecting state and move to the last non-underscore character.
Skipping0 _ Expecting0 _ L
Skipping1 _ Expecting1 _ L
Skipping2 _ Expecting2 _ L
// If the character is as expected, reverse.
Expecting0 0 Reversing _ L
Expecting1 1 Reversing _ L
Expecting2 2 Reversing _ L
// If there is no character, then
// the last character that has started the skipping
// is the only character remaining in the tape
// and individual characters are palindromes.
Expecting0 _ Confirmation _ L
Expecting1 _ Confirmation _ L
Expecting2 _ Confirmation _ L
// Reversing means skipping backwards, until an underscore is reached.
// Then start reading.
Reversing _ Reading _ R
Reversing 0 Reversing 0 L
Reversing 1 Reversing 1 L
Reversing 2 Reversing 2 L