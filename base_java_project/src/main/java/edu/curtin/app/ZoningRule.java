package edu.curtin.app;

//Zoning rule parent class (decorator pattern). Used to wrap the base class
public abstract class ZoningRule implements Square {
    protected Square next;

    public ZoningRule(Square inNext) {
        next = inNext;
    }
}
