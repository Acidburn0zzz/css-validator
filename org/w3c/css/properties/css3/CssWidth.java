// $Id$
//
// (c) COPYRIGHT MIT, ERCIM and Keio University
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.css.properties.css3;

import org.w3c.css.properties.css.CssProperty;
import org.w3c.css.util.ApplContext;
import org.w3c.css.util.InvalidParamException;
import org.w3c.css.values.CssExpression;
import org.w3c.css.values.CssIdent;
import org.w3c.css.values.CssLength;
import org.w3c.css.values.CssNumber;
import org.w3c.css.values.CssPercentage;
import org.w3c.css.values.CssTypes;
import org.w3c.css.values.CssValue;

/**
 * @version $Revision$
 * @spec http://www.w3.org/TR/2007/WD-css3-box-20070809/#width
 */
public class CssWidth extends org.w3c.css.properties.css.CssWidth {

    CssLength lenVal;
    CssPercentage perVal;
    CssIdent identVal;

    /**
     * Create a new CssWidth
     */
    public CssWidth() {
    }

    /**
     * Create a new CssWidth.
     *
     * @param expression The expression for this property
     * @throws org.w3c.css.util.InvalidParamException Values are incorrect
     */
    public CssWidth(ApplContext ac, CssExpression expression, boolean check)
            throws InvalidParamException {

        if (check && expression.getCount() > 1) {
            throw new InvalidParamException("unrecognize", ac);
        }

        CssValue val = expression.getValue();

        setByUser();

        switch (val.getType()) {
            case CssTypes.CSS_IDENT:
                CssIdent ident = (CssIdent) val;
                if (inherit.equals(val)) {
                    identVal = inherit;
                } else if (initial.equals(val)) {
                    identVal = initial;
                } else if (auto.equals(val)) {
                    identVal = auto;
                } else {
                    throw new InvalidParamException("unrecognize", ac);
                }
                break;
            case CssTypes.CSS_NUMBER:
                val = ((CssNumber) val).getLength();
            case CssTypes.CSS_LENGTH:
                lenVal = (CssLength) val;
                if (!lenVal.isPositive()) {
                    throw new InvalidParamException("negative-value",
                            val.toString(), ac);
                }
                break;
            case CssTypes.CSS_PERCENTAGE:
                perVal = (CssPercentage) val;
                if (perVal.floatValue() < 0.) {
                    throw new InvalidParamException("negative-value",
                            val.toString(), ac);
                }
                break;
            default:
                throw new InvalidParamException("value", val, getPropertyName(), ac);
        }
        expression.next();
    }

    public CssWidth(ApplContext ac, CssExpression expression)
            throws InvalidParamException {
        this(ac, expression, false);
    }

    /**
     * Returns the value of this property.
     */
    public Object get() {
        if (identVal != null) {
            return identVal;
        }
        if (perVal != null) {
            return perVal;
        }
        return lenVal;
    }

    /**
     * Returns true if this property is "softly" inherited
     * e.g. his value equals inherit
     */
    public boolean isSoftlyInherited() {
        return identVal == inherit;
    }

    /**
     * Returns a string representation of the object.
     */
    public String toString() {
        if (identVal != null) {
            return identVal.toString();
        }
        if (perVal != null) {
            return perVal.toString();
        }
        if (lenVal != null) {
            return lenVal.toString();
        }
        // the default
        return auto.toString();
    }

    /**
     * Compares two properties for equality.
     *
     * @param property The other property.
     */
    public boolean equals(CssProperty property) {
        try {
            CssWidth w = (CssWidth) property;
            return (identVal == w.identVal) &&
                    ((perVal == null && w.perVal == null) ||
                            (perVal != null && perVal.equals(w.perVal))) &&
                    ((lenVal == null && w.lenVal == null) ||
                            (lenVal != null && lenVal.equals(w.lenVal)));

        } catch (ClassCastException ex) {
            return false;
        }
    }

    /**
     * Is the value of this property is a default value.
     * It is used by all macro for the function <code>print</code>
     */
    public boolean isDefault() {
        return ((identVal == auto) || (identVal == initial));
    }

}
