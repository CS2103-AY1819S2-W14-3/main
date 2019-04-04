package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.MapGrid;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.player.Player;
import seedu.address.model.statistics.PlayerStatistics;

/**
 * Represents a storage for {@link MapGrid}.
 */
public interface StatisticsStorage {

    Path getStatisticsFilePath();


    void saveStatisticsData(PlayerStatistics statisticsData) throws IOException;
    void saveStatisticsData(PlayerStatistics statisticsData, Path filePath) throws IOException;

    Optional<PlayerStatistics> readStatisticsData() throws DataConversionException, IOException;
    Optional<PlayerStatistics> readStatisticsData(Path filePath) throws DataConversionException, IOException;
//    /**
//     * Returns MapGrid data as a {@link ReadOnlyAddressBook}.
//     *   Returns {@code Optional.empty()} if storage file is not found.
//     * @throws DataConversionException if the data in storage is not in the expected format.
//     * @throws IOException if there was any problem when reading from the storage.
//     */
//    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;
//
//    /**
//     * @see #getAddressBookFilePath()
//     */
//    Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException;
//
//    /**
//     * Saves the given {@link ReadOnlyAddressBook} to the storage.
//     * @param addressBook cannot be null.
//     * @throws IOException if there was any problem writing to the file.
//     */
//    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
//
//    /**
//     * @see #saveAddressBook(ReadOnlyAddressBook)
//     */
//    void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException;
//
//    /**
//     * Saves the ReadOnlyAddressBook locally in a fixed temporary location.
//     *
//     * @param addressBook cannot be null.
//     * @throws IOException if there was any problem writing to the file.
//     */
//    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

}
