package fr.efaya.domain.filereader;

import fr.efaya.domain.AccountRecord;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by KTIFA FAMILY on 25/05/2016.
 */
public interface FileParser {

    boolean accept(String fileType);

    List<AccountRecord> parseFile(File file, String username) throws IOException;
}
