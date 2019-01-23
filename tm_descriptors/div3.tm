// Divisible by 3
states 6
Clearing +
Buff1
Return1
Buff10
Return10
Return01
// ALPHABET
alphabet 2 0 1
///////////////////////////////////////////
// Remove zeros as they are essentially multiplying the number by 2.
Clearing _ Clearing _ R
Clearing 0 Clearing _ R
// Subtract 11.
// Pattern 11
Clearing 1 Buff1 1 R
Buff1 1 Return1 _ L
Return1 1 Clearing _ R
// Pattern 100
Buff1 0 Buff10 0 R
Buff10 0 Return10 1 L
Return10 0 Return1 _ L
// Pattern 101
Buff10 1 Return01 0 L
Return01 0 Return1 1 L