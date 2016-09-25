package pro.rgun.akbarstest.domain.use_case.note;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import pro.rgun.akbarstest.domain.model.Note;
import pro.rgun.akbarstest.domain.repository.NotesRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by rgun on 25.09.16.
 */
public class GetNoteListTest {

    @Mock
    private NotesRepository mockNotesRepository1, mockNotesRepository2;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void Note_Add_Success(){
        // Arrange
        GetNoteList getNoteList = new GetNoteList(mockNotesRepository1);
        // Act
        List<Note> noteList = getNoteList.getNoteList();
        // Assert
        verify(mockNotesRepository1).getAllNotes();
        verifyNoMoreInteractions(mockNotesRepository1);
    }


    @Test
    public void Note_ChangeRepository_Success(){
        // Arrange
        GetNoteList getNoteList = new GetNoteList(mockNotesRepository1);
        // Act
        getNoteList.getNoteList();
        getNoteList.setRepository(mockNotesRepository2);
        getNoteList.getNoteList();
        // Assert
        verify(mockNotesRepository1).getAllNotes();
        verifyNoMoreInteractions(mockNotesRepository1);
        verify(mockNotesRepository2).getAllNotes();
        verifyNoMoreInteractions(mockNotesRepository2);
    }
}