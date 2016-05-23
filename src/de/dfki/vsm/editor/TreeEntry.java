package de.dfki.vsm.editor;

//~--- JDK imports ------------------------------------------------------------

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import java.io.IOException;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

// TODO: move to util package
public final class TreeEntry extends DefaultMutableTreeNode implements Transferable {
    private final String mText;
    private final Icon   mIcon;
    private final Object mData;

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public TreeEntry(final String text, final Icon icon, final Object data) {
        mText = text;
        mIcon = icon;
        mData = data;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final String getText() {
        return mText;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final Icon getIcon() {
        return mIcon;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public final Object getData() {
        return mData;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public final Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return mData;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public final boolean isDataFlavorSupported(final DataFlavor flavor) {
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public final DataFlavor[] getTransferDataFlavors() {
        return null;
    }
}
