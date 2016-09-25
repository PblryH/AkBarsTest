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
public class AddNoteTest {

    @Mock
    private NotesRepository mockNotesRepository1, mockNotesRepository2;

    @Mock
    private Note mockNote1, mockNote2;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void Note_Add_Success(){
        // Arrange
        AddNote addNote = new AddNote(mockNotesRepository1);
        // Act
        addNote.addNote(mockNote1);
        // Assert
        verify(mockNotesRepository1).addNote(mockNote1);
        verifyNoMoreInteractions(mockNotesRepository1);
    }


    @Test
    public void Note_ChangeRepository_Success(){
        // Arrange
        AddNote addNote = new AddNote(mockNotesRepository1);
        // Act
        addNote.addNote(mockNote1);
        addNote.setRepository(mockNotesRepository2);
        addNote.addNote(mockNote2);
        // Assert
        verify(mockNotesRepository1).addNote(mockNote1);
        verifyNoMoreInteractions(mockNotesRepository1);
        verify(mockNotesRepository2).addNote(mockNote2);
        verifyNoMoreInteractions(mockNotesRepository2);
    }
}