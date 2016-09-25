package pro.rgun.akbarstest.domain.use_case.note;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.NotesRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by rgun on 25.09.16.
 */
public class SaveNoteTest {

    @Mock
    private NotesRepository mockNotesRepository1, mockNotesRepository2;

    private String mockNoteId1 = "123", mockNoteId2 = "456";

    @Mock
    private Note mockNote1, mockNote2;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void Note_Save_Success(){
        // Arrange
        SaveNote saveNote = new SaveNote(mockNotesRepository1);
        // Act
        saveNote.saveNote(mockNoteId1, mockNote1);
        // Assert
        verify(mockNotesRepository1).saveNote(mockNoteId1, mockNote1);
        verifyNoMoreInteractions(mockNotesRepository1);
    }


    @Test
    public void Note_ChangeRepository_Success(){
        // Arrange
        SaveNote saveNote = new SaveNote(mockNotesRepository1);
        // Act
        saveNote.saveNote(mockNoteId1, mockNote1);
        saveNote.setRepository(mockNotesRepository2);
        saveNote.saveNote(mockNoteId2, mockNote2);
        // Assert
        verify(mockNotesRepository1).saveNote(mockNoteId1, mockNote1);
        verifyNoMoreInteractions(mockNotesRepository1);
        verify(mockNotesRepository2).saveNote(mockNoteId2, mockNote2);
        verifyNoMoreInteractions(mockNotesRepository2);
    }
}