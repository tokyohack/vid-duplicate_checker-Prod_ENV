// DBに重複があるかbool返す
package video;

import java.util.Objects;

public class isUnique {
    private boolean bRet;

    public isUnique(Object nameForsql, String nameForArg, Object sizeForsql, long sizeForArg, Object dateForSql, long dateForArg) {
        this.setterIsUnique(Objects.equals((String) nameForsql, nameForArg) && (long) sizeForsql == sizeForArg && (long) dateForSql == dateForArg);
    }

    private void setterIsUnique(boolean bRet) {
        this.bRet = bRet;
    }

    public boolean getterIsUnique() {
        return this.bRet;
    }
}