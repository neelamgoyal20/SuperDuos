package barqsoft.footballscores.service;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.IBinder;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.dto.Scores;

/**
 * Created by E01090 on 3/14/2016.
 */
public class FootballWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FoolballWidgetRemoteViewsFactory(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    class FoolballWidgetRemoteViewsFactory implements RemoteViewsFactory{

        private Context mContext;
        private List<Scores> scoresList = new ArrayList<>();

        FoolballWidgetRemoteViewsFactory(Context ctx){
            this.mContext = ctx;
        }
        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            Uri uri = DatabaseContract.scores_table.buildScoreWithDate();

            Date todayDate = new Date();
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(todayDate);

            Cursor cursor = mContext.getContentResolver().query(uri, null, DatabaseContract.scores_table.DATE_COL, new String[]{formattedDate}, null);
            if(cursor.moveToFirst()){
                scoresList = new ArrayList<>();
                while(cursor.moveToNext()){
                    String teamA = cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.HOME_COL));
                    String teamB = cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.AWAY_COL));
                    String scoreA = cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.HOME_GOALS_COL));
                    String scoreB = cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.AWAY_GOALS_COL));
                    scoresList.add(new Scores(teamA, scoreA, teamB, scoreB));
                }
            }
            cursor.close();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return scoresList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if(scoresList.size() == 0){
                return null;
            }

            RemoteViews scoreView = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_layout);

            Scores score = scoresList.get(position);
            scoreView.setTextViewText(R.id.teamA, score.getTeamA());
            scoreView.setTextViewText(R.id.teamB, score.getTeamB());

            //If the game hasn't started yet, show the time instead.
//            final int awayScore = Integer.parseInt(score.getTeamAScore());
//            final int homeScore = Integer.parseInt(score.getTeamBScore());

            scoreView.setTextViewText(R.id.scoreA, score.getTeamAScore());
            scoreView.setTextViewText(R.id.scoreB, score.getTeamBScore());

            /*//The game hasn't started yet
            if(awayScore == -1 || homeScore == -1){
                //So hide the scores and show the time
                scoreView.setViewVisibility(R.id.score_column, View.GONE);
                scoreView.setViewVisibility(R.id.time_column, View.VISIBLE);
                scoreView.setTextViewText(R.id.game_time, score.getGameTime());
            } else {
                scoreView.setViewVisibility(R.id.score_column, View.VISIBLE);
                scoreView.setViewVisibility(R.id.time_column, View.GONE);
                scoreView.setTextViewText(R.id.home_team_score, score.getHomeTeamScore());
                scoreView.setTextViewText(R.id.away_team_score, score.getAwayTeamScore());

                //The game is in progress or finished, so highlight the winning team
                final int tieGameId = -1;
                final boolean homeTeamIsWinning = homeScore > awayScore;
                final boolean awayTeamIsWinning = awayScore > homeScore;
                int winningTeamScoreId = homeTeamIsWinning ? R.id.home_team_score: awayTeamIsWinning ? R.id.away_team_score : tieGameId;
                int winningTeamNameId = homeTeamIsWinning ? R.id.home_team_name: awayTeamIsWinning ? R.id.away_team_name : tieGameId;
                scoreView.setTextColor(winningTeamScoreId, Color.BLACK);
                scoreView.setTextColor(winningTeamNameId, Color.BLACK);
            }*/

            return scoreView;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
