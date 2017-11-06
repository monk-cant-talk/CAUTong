package tong.cau.com.cautong.model;

import java.util.List;
import java.util.Map;

/**
 * Created by xzvfi on 2017-11-04.
 */

public class ParseRule {
    private String tag;
    private boolean rowSpaced;
    private Map<String, String> tableAttrs;
    private int tableIndex;
    private int firstRowIndex;

    private Meta titleMeta;
    private Meta authorMeta;
    private Meta dateMeta;

    private String contentParseType;
    private String contentTag;
    private Map<String, String> properties;

    public class Meta {
        private int tdIndex;
        private List<String> hierarchy;
        private String etc;

        public int getTdIndex() {
            return tdIndex;
        }

        public void setTdIndex(int tdIndex) {
            this.tdIndex = tdIndex;
        }

        public List<String> getHierarchy() {
            return hierarchy;
        }

        public void setHierarchy(List<String> hierarchy) {
            this.hierarchy = hierarchy;
        }

        public String getEtc() {
            return etc;
        }

        public void setEtc(String etc) {
            this.etc = etc;
        }
    }

    public String getTableQuery(String key) {
        return "[" + key + "=" + tableAttrs.get(key) + "]";
    }

    public String getContentQuery(String key, String parseType) {
        if (parseType.equals("attrs")) {
            return String.format("[%s=%s]", key, properties.get(key));
        } else if (parseType.equals("class")) {
            return String.format(".%s", properties.get(key));
        } else if (parseType.equals("id")) {
            return String.format("#%s", properties.get(key));
        } else {
            return null;
        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isRowSpaced() {
        return rowSpaced;
    }

    public void setRowSpaced(boolean rowSpaced) {
        this.rowSpaced = rowSpaced;
    }

    public Map<String, String> getTableAttrs() {
        return tableAttrs;
    }

    public void setTableAttrs(Map<String, String> tableAttrs) {
        this.tableAttrs = tableAttrs;
    }

    public int getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(int tableIndex) {
        this.tableIndex = tableIndex;
    }

    public int getFirstRowIndex() {
        return firstRowIndex;
    }

    public void setFirstRowIndex(int firstRowIndex) {
        this.firstRowIndex = firstRowIndex;
    }

    public Meta getTitleMeta() {
        return titleMeta;
    }

    public void setTitleMeta(Meta titleMeta) {
        this.titleMeta = titleMeta;
    }

    public Meta getAuthorMeta() {
        return authorMeta;
    }

    public void setAuthorMeta(Meta authorMeta) {
        this.authorMeta = authorMeta;
    }

    public Meta getDateMeta() {
        return dateMeta;
    }

    public void setDateMeta(Meta dateMeta) {
        this.dateMeta = dateMeta;
    }

    public String getContentParseType() {
        return contentParseType;
    }

    public void setContentParseType(String contentParseType) {
        this.contentParseType = contentParseType;
    }

    public String getContentTag() {
        return contentTag;
    }

    public void setContentTag(String contentTag) {
        this.contentTag = contentTag;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
