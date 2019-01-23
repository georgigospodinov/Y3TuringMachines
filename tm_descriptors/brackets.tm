// Check brackets of input.
// Each opening bracket needs a closing bracket.
// Brackets need to be nested correctly.
// Check by looking for the first closing bracket, match an opening bracket
// and check for other opened brackets.
// Use 'x' to denote start and end of tape.
// Underscore '_' is used to remove brackets that have been matched.
states 7
Start
FindClosing
MatchClosing(
MatchClosing[
MatchClosing{
Check +
Invalid
alphabet 7 ( ) [ ] { } x
Start x FindClosing x R
///////////////////////////////////////////////////////////////////////////////////
// Move to the right until a closing bracket is found /////////////////////////////
FindClosing _ FindClosing _ R
FindClosing ( FindClosing ( R
FindClosing [ FindClosing [ R
FindClosing { FindClosing { R
FindClosing ) MatchClosing( _ L
FindClosing ] MatchClosing[ _ L
FindClosing } MatchClosing{ _ L
FindClosing x Check x L
///////////////////////////////////////////////////////////////////////////////////
// Previous Character must be opening bracket /////////////////////////////////////
MatchClosing( _ MatchClosing( _ L
MatchClosing( ( FindClosing _ R
MatchClosing[ _ MatchClosing[ _ L
MatchClosing[ [ FindClosing _ R
MatchClosing{ _ MatchClosing{ _ L
MatchClosing{ { FindClosing _ R
///////////////////////////////////////////////////////////////////////////////////
// Make sure there are no brackets left ///////////////////////////////////////////
Check _ Check _ L
Check ( Invalid ( L
Check ) Invalid ) L
Check [ Invalid [ L
Check ] Invalid ] L
Check { Invalid { L
Check } Invalid } L