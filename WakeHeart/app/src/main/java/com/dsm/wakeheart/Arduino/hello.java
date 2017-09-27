package com.dsm.wakeheart.Arduino;

class hello {
    private static final hello ourInstance = new hello();

    static hello getInstance() {
        return ourInstance;
    }

    private hello() {
    }
}
