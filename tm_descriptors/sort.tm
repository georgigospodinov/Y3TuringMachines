// Sorts an array of digits (0, 1, 2)
states 10
Sorted +
Previous1 +
Previous2 +
Shifting_0_Between_1s
Shifting_0_Between_2s
Shifting_1_Between_2s
Place0After1
Place0After2
Place1After2
Push1_Through2
///////////////////////////////////////////
// Alphabet
alphabet 3 0 1 2
///////////////////////////////////////////
///////////////////////////////////////////
///////////////////////////////////////////
Sorted _ Sorted _ R
Sorted 0 Sorted 0 R
Sorted 1 Previous1 1 R
Sorted 2 Previous2 2 R
Previous1 2 Previous2 2 R
///////////////////////////////////////////
// Shift 0 back between 1s
Previous1 0 Shifting_0_Between_1s 1 L
Shifting_0_Between_1s 1 Shifting_0_Between_1s 1 L
Shifting_0_Between_1s 0 Place0After1 0 R
Shifting_0_Between_1s _ Place0After1 _ R
Place0After1 1 Sorted 0 R
///////////////////////////////////////////
// Shift 0 back between 2s
Previous2 0 Shifting_0_Between_2s 2 L
Shifting_0_Between_2s 2 Shifting_0_Between_2s 2 L
Shifting_0_Between_2s 1 Push1_Through2 1 R
Push1_Through2 2 Shifting_0_Between_1s 1 L
Shifting_0_Between_2s 0 Place0After2 0 R
Shifting_0_Between_2s _ Place0After2 _ R
Place0After2 2 Sorted 0 R
///////////////////////////////////////////
// Shift 1 back between 2s
Previous2 1 Shifting_1_Between_2s 2 L
Shifting_1_Between_2s 2 Shifting_1_Between_2s 2 L
Shifting_1_Between_2s 1 Place1After2 1 R
Shifting_1_Between_2s 0 Place1After2 0 R
Shifting_1_Between_2s _ Place1After2 _ R
Place1After2 2 Sorted 1 R
///////////////////////////////////////////
// Skip same
Previous1 1 Previous1 1 R
Previous2 2 Previous2 2 R