pragma solidity ^0.4.0;

contract SimpleStorage {

  	// @dev indicates the name of a thing.
  	bytes32 public name;
    
    uint storedData;

	constructor(bytes32 _name) public {
    	name = _name;
    	storedData = 5;
    }

    function set(uint x) {
        storedData = x;
    }

    function get() constant returns (uint retVal) {
        return storedData;
    }
}
