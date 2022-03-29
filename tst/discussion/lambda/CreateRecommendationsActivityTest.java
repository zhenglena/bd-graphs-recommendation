package discussion.lambda;

import com.amazon.ata.graphs.dynamodb.FollowEdge;
import com.amazon.ata.graphs.dynamodb.FollowEdgeDao;
import com.amazon.ata.graphs.dynamodb.Recommendation;
import com.amazon.ata.graphs.dynamodb.RecommendationDao;
import com.amazon.ata.graphs.lambda.CreateRecommendationsActivity;
import com.amazon.ata.graphs.lambda.CreateRecommendationsRequest;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.security.InvalidParameterException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateRecommendationsActivityTest {
    private CreateRecommendationsActivity activity;

    @Mock
    private RecommendationDao recommendationDao;
    @Mock
    private FollowEdgeDao followEdgeDao;
    @Mock
    private DynamoDBMapper mapper;
    @Mock
    private PaginatedQueryList<FollowEdge> queryResult;
    @Mock
    private PaginatedQueryList<FollowEdge> queryResult2;

    @BeforeEach
    private void setup() {
        initMocks(this);
        activity = new CreateRecommendationsActivity(recommendationDao, followEdgeDao);
    }

    @Test
    void handleRequest_withNullUserName_throwException() {
        // GIVEN
        // null username
        String username = null;

        // WHEN + THEN
        assertThrows(InvalidParameterException.class, () -> activity.handleRequest(new CreateRecommendationsRequest(username, 3), null));
    }

    @Test
    void handleRequest_withValidUsername_returnsProperCountOfRecommendations() {
        // GIVEN
        String username = "username1";
        String username2 = "username2";
        String username3 = "username3";
        String username4 = "username4";

        // mock Recommendations
        PaginatedQueryList<Recommendation> recommendations = Mockito.mock(PaginatedQueryList.class);
        when(mapper.query(eq(Recommendation.class), any(DynamoDBQueryExpression.class)))
                .thenReturn(queryResult);

        // mock FollowEdgeDao
        queryResult.add(new FollowEdge(username, username2));
        queryResult2.add(new FollowEdge(username2, username3)); // this should be the only recommendation
        queryResult2.add(new FollowEdge(username2, username3)); // test for duplicates
        queryResult2.add(new FollowEdge(username2, username)); // test that current user doesn't get added
        queryResult2.add(new FollowEdge(username2, username2)); // test that already followed user doesn't get added
        when(mapper.query(eq(FollowEdge.class), any(DynamoDBQueryExpression.class)))
                .thenReturn(queryResult);

        when(followEdgeDao.getAllFollows(username)).thenReturn(queryResult);
        when(followEdgeDao.getAllFollows(username2)).thenReturn(queryResult);
        when(recommendationDao.getRecommendations(username2)).thenReturn(recommendations);

        Stream.Builder<FollowEdge> builder = Stream.builder();
        Stream<FollowEdge> stream = builder.add(new FollowEdge(username, username2)).build();

        builder = Stream.builder();
        Stream<FollowEdge> stream2 = builder.add(new FollowEdge(username2, username3)).build();

        when(queryResult.stream()).thenReturn(stream, stream2);

        // WHEN
        int count = activity.handleRequest(new CreateRecommendationsRequest(username, 5), null).size();

        // THEN
        assertEquals(1, count);
    }

    @Test
    void handleRequest_withValidUsername_returnsProperCountOfRecommendedFollows() {
    }

}
