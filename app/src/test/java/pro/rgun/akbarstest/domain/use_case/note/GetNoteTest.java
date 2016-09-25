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
public class GetNoteTest {

    @Mock
    private NotesRepository mockNotesRepository1, mockNotesRepository2;

    private String mockNoteId1 = "123", mockNoteId2 = "456";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void Note_Get_Success(){
        // Arrange
        GetNote getNote = new GetNote(mockNotesRepository1);
        // Act
        Note note = getNote.getNote(mockNoteId1);
        // Assert
        verify(mockNotesRepository1).getNote(mockNoteId1);
        verifyNoMoreInteractions(mockNotesRepository1);
    }


    @Test
    public void Note_ChangeRepository_Success(){
        // Arrange
        GetNote getNote = new GetNote(mockNotesRepository1);
        // Act
        getNote.getNote(mockNoteId1);
        getNote.setRepository(mockNotesRepository2);
        getNote.getNote(mockNoteId2);
        // Assert
        verify(mockNotesRepository1).getNote(mockNoteId1);
        verifyNoMoreInteractions(mockNotesRepository1);
        verify(mockNotesRepository2).getNote(mockNoteId2);
        verifyNoMoreInteractions(mockNotesRepository2);
    }
}