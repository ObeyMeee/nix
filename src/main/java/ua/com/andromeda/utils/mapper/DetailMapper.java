package ua.com.andromeda.utils.mapper;

import org.bson.Document;
import ua.com.andromeda.model.Detail;

public class DetailMapper {

    private static final String ID = "_id";

    private DetailMapper() {

    }


    public static Document mapDetailToDocument(Detail detail) {
        Document document = new Document();
        document.append(ID, detail.getId());
        document.append("name", detail.getName());
        return document;
    }

    public static Detail mapDocumentToDetail(Document document) {
        Detail detail = new Detail();
        detail.setId(document.getString(ID));
        detail.setName(document.getString("name"));
        return detail;
    }
}
