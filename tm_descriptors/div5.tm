// Divisible by 5
states 12
Clearing +
Buff1
Buff10
Buff100
Buff11
Return1
Return10
Return11
Return01
Return100
Return1000
Return1001
// ALPHABET
alphabet 2 0 1
///////////////////////////////////////////
// Remove zeros as they are essentially multiplying the number by 2.
Clearing _ Clearing _ R
Clearing 0 Clearing _ R
Clearing 1 Buff1 1 R
// Subtract 101.
// Pattern 111
Buff1 1 Buff11 1 R
Buff11 1 Return11 0 L
Return11 1 Return1 1 L
Return1 1 Clearing _ R
// Pattern 110
Buff11 0 Return01 1 L
Return01 1 Return1 _ L
// Pattern 101
Buff1 0 Buff10 0 R
Buff10 1 Return10 _ L
Return10 0 Return1 _ L
// Pattern 1001
Buff10 0 Buff100 0 R
Buff100 1 Return1001 0 L
Return1001 0 Return100 1 L
Return100 0 Return1 _ L
// Patter 1000
Buff100 0 Return1000 1 L
Return1000 0 Return10 1 L