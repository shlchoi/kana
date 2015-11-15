package ca.uwaterloo.sh6choi.kana.presentation;

import java.util.List;

import ca.uwaterloo.sh6choi.kana.database.DatabaseRequestCallback;
import ca.uwaterloo.sh6choi.kana.database.HiraganaCharacterDataSource;
import ca.uwaterloo.sh6choi.kana.database.KanaCharacterDataSource;
import ca.uwaterloo.sh6choi.kana.database.KatakanaCharacterDataSource;
import ca.uwaterloo.sh6choi.kana.model.KanaCharacter;

/**
 * Created by Samson on 2015-11-10.
 */
public abstract class KanaCharacterPresenter {
    protected final CharacterView mView;
    protected KanaCharacterDataSource mDataSource;

    protected KanaCharacterPresenter(KanaCharacterDataSource dataSource, CharacterView view) {
        mDataSource = dataSource;
        mView = view;
    }

    public void obtainAllCharacters() {
        mDataSource.open();
        mDataSource.queryKana(new DatabaseRequestCallback<List<KanaCharacter>>() {
            @Override
            public void processResults(List<KanaCharacter> results) {
                mView.refreshCharacterList(results);
                mDataSource.close();
            }
        });
    }

    public void obtainRegularCharacters() {
        mDataSource.open();
        mDataSource.queryKanaFiltered(new DatabaseRequestCallback<List<KanaCharacter>>() {
            @Override
            public void processResults(List<KanaCharacter> results) {
                mView.refreshCharacterList(results);
                mDataSource.close();
            }
        });
    }

    public interface CharacterView {
        void refreshCharacterList(List<KanaCharacter> kanaCharacterList);
    }
}
