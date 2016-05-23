package de.dfki.vsm.runtime.interpreter.symbol;

//~--- non-JDK imports --------------------------------------------------------

import de.dfki.vsm.runtime.interpreter.error.InterpreterError;
import de.dfki.vsm.runtime.interpreter.value.AbstractValue;
import de.dfki.vsm.util.cpy.Copyable;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author Not me
 */
public final class SymbolTable implements Copyable {

    // The Symbol Table Map
    private final HashMap<String, SymbolEntry> mSymbolTable;

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public SymbolTable() {
        mSymbolTable = new HashMap<>();
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public SymbolTable(final HashMap<String, SymbolEntry> table) {
        mSymbolTable = table;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final HashMap<String, SymbolEntry> getSymbolTable() {
        return mSymbolTable;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final HashMap<String, SymbolEntry> copySymbolTable() {

        // Create A Copy Of The Table
        final HashMap<String, SymbolEntry> copy = new HashMap<>();

        // Copy Each Single Table Entry
        for (Entry<String, SymbolEntry> entry : mSymbolTable.entrySet()) {
            copy.put(entry.getKey(), entry.getValue().getCopy());
        }

        // Return Copy Of The Table
        return copy;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final boolean contains(final SymbolEntry entry) {
        return mSymbolTable.containsValue(entry);
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final boolean contains(final String symbol) {
        return mSymbolTable.containsKey(symbol);
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final void create(final String symbol, final AbstractValue value) throws InterpreterError {
        mSymbolTable.put(symbol, new SymbolEntry(symbol, value));
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final AbstractValue write(final String symbol, final AbstractValue value) throws InterpreterError {
        return mSymbolTable.get(symbol).write(value);
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final AbstractValue write(final String symbol, final int index, final AbstractValue value)
            throws InterpreterError {
        return mSymbolTable.get(symbol).write(value, index);
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final AbstractValue write(final String symbol, final String member, final AbstractValue value)
            throws InterpreterError {
        return mSymbolTable.get(symbol).write(value, member);
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final AbstractValue read(final String symbol) {
        return mSymbolTable.get(symbol).read();
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final AbstractValue read(final String symbol, final int index) throws InterpreterError {
        return mSymbolTable.get(symbol).read(index);
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final AbstractValue read(final String symbol, final String member) throws InterpreterError {
        return mSymbolTable.get(symbol).read(member);
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public final SymbolTable getCopy() {
        return new SymbolTable(copySymbolTable());
    }
}
