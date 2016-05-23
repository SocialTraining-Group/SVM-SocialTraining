package de.dfki.vsm.model.sceneflow.command;

//~--- non-JDK imports --------------------------------------------------------

import de.dfki.vsm.model.sceneflow.command.expression.Expression;
import de.dfki.vsm.util.ios.IOSIndentWriter;
import de.dfki.vsm.util.xml.XMLParseAction;
import de.dfki.vsm.util.xml.XMLParseError;
import de.dfki.vsm.util.xml.XMLWriteError;
import java.util.ArrayList;

import org.w3c.dom.Element;

//~--- JDK imports ------------------------------------------------------------


/**
 * @author Not me
 */
public class Play extends Command {
    private Expression         mArg;
    private ArrayList<Expression> mArgList;

    public Play() {
        mArg     = null;
        mArgList = new ArrayList<Expression>();
    }

    public Play(Expression arg) {
        mArg     = arg;
        mArgList = new ArrayList<Expression>();
    }

    public Play(Expression arg, ArrayList<Expression> argList) {
        mArg     = arg;
        mArgList = argList;
    }

    public Expression getArg() {
        return mArg;
    }

    public void setArg(Expression arg) {
        mArg = arg;
    }

    public ArrayList<Expression> getArgList() {
        return mArgList;
    }

    public void setArgList(ArrayList<Expression> value) {
        mArgList = value;
    }

    public Expression getArgAt(int index) {
        return mArgList.get(index);
    }

    public void setArgAt(int index, Expression value) {
        mArgList.set(index, value);
    }

    public boolean addArg(Expression value) {
        return mArgList.add(value);
    }

    public int getSizeOfArgList() {
        return mArgList.size();
    }

    public ArrayList<Expression> getCopyOfArgList() {
        ArrayList<Expression> copy = new ArrayList<Expression>();

        for (Expression exp : mArgList) {
            copy.add(exp.getCopy());
        }

        return copy;
    }

    public CmdType getCmdType() {
        return CmdType.PLAY;
    }

    public String getAbstractSyntax() {
        String desc = "Play" + " ( ";

        desc += ((mArg != null)
                 ? mArg.getAbstractSyntax()
                 : "");

        for (int i = 0; i < mArgList.size(); i++) {
            desc += " , " + mArgList.get(i).getAbstractSyntax();
        }

        return desc + " ) ";
    }

    public String getConcreteSyntax() {
        String desc = "Play" + " ( ";

        desc += ((mArg != null)
                 ? mArg.getConcreteSyntax()
                 : "");

        for (int i = 0; i < mArgList.size(); i++) {
            desc += " , " + mArgList.get(i).getConcreteSyntax();
        }

        return desc + " ) ";
    }

    public String getFormattedSyntax() {
        String desc = "#p#Play" + " ( ";

        desc += ((mArg != null)
                 ? mArg.getFormattedSyntax()
                 : "");

        for (int i = 0; i < mArgList.size(); i++) {
            desc += " , " + mArgList.get(i).getFormattedSyntax();
        }

        return desc + " ) ";
    }

    public Play getCopy() {
        return new Play(mArg.getCopy(), getCopyOfArgList());
    }

    public void writeXML(IOSIndentWriter out) throws XMLWriteError {
        out.println("<Play>").push();

        if (mArg != null) {
            mArg.writeXML(out);
        }

        for (int i = 0; i < mArgList.size(); i++) {
            mArgList.get(i).writeXML(out);
        }

        out.pop().println("</Play>");
    }

    public void parseXML(Element element) throws XMLParseError {
        final ArrayList<Expression> expList = new ArrayList<Expression>();

        XMLParseAction.processChildNodes(element, new XMLParseAction() {
            public void run(Element element) throws XMLParseError {
                expList.add(Expression.parse(element));
            }
        });
        mArg     = expList.remove(0);
        mArgList = expList;
    }
}
