# Graphs - Finding Friends

One of the most common use cases for graphs is social networks.
We have an updated version of the Discussion CLI to allow users
to follow one another. Note that this is a _directional_ relationship.

## Phase 0: Determining the Relationship

Since we are continuing work on the Discussion CLI, we want to deploy new versions of the tables that we can
work with without impacting our previous lesson's work.

The new tables are:
* `Graphs-FollowEdges`: containing members of the discussion app
* `Graphs-Recommendations`: the actual messages that members have posted

1. Create the tables we'll be using for this activity by running the following `aws` CLI commands (double check that the region is set to where you want it):
   ```none
   aws cloudformation create-stack --region us-west-2 --stack-name graphs-memberstable --template-body file://cloudformation/discussion_cli_table_members.yaml --capabilities CAPABILITY_IAM
   aws cloudformation create-stack --region us-west-2 --stack-name graphs-messagestable --template-body file://cloudformation/discussion_cli_table_messages.yaml --capabilities CAPABILITY_IAM
   aws cloudformation create-stack --region us-west-2 --stack-name graphs-topicstable --template-body file://cloudformation/discussion_cli_table_topics.yaml --capabilities CAPABILITY_IAM
   aws cloudformation create-stack --region us-west-2 --stack-name graphs-followedgestable --template-body file://cloudformation/discussion_cli_table_follow_edges.yaml --capabilities CAPABILITY_IAM
   aws cloudformation create-stack --region us-west-2 --stack-name graphs-recommendstable --template-body file://cloudformation/discussion_cli_table_recommendations.yaml --capabilities CAPABILITY_IAM
   ```
1. Make sure the `aws` command runs without error.
1. Log into your AWS account and verify that the tables exist and have sample data.

**GOAL:** `Graphs-FollowEdges` and `Graphs-Recommendations` tables are created in your AWS Account, including sample data.

Phase 0 is complete when:
* `Graphs-FollowEdges` and `Graphs-Recommendations`
  tables exist in your Unit 3 AWS account with some sample data

### Phase 1: No Rough Edges
With this version of the CLI incorporates a graph structure, we need to understand the models that help us
accomplish this in code. You may remember the `Member` class from before. This class has remained unchanged,
but in terms of the graph structure, `Member`s represent our nodes or vertices. As for the edges, we've added the 
`FollowEdge` class. This class contains only two fields: `fromUsername` and `toUsername`. These two
fields represent the edge's relationship to the two nodes. `fromUsername` is following `toUsername`. 

Let's update our `FollowEdgeDao` to include a few common use cases. Usually our DAOs contain methods for CRUD
functionality, but in the case of `FollowEdge`, we will build a `getAllFollowers` and `getAllFollows` methods as well.
Your job is to go into `FollowEdgeDao` and implement both methods.

GOAL: Implement the `FollowEdgeDao`'s two empty methods, meeting the following criteria:

`getAllFollowers`
1. Retrieve all `FollowEdge` objects that have the username that was passed in as the `fromUsername` field.
2. If a null or empty username is given, return an `IllegalArgumentException`.

`getAllFollowing`
1. Retrieve all `FollowEdge` objects that have the username that was passed in as the `toUsername` field.
2. If a null or empty username is given, return an `IllegalArgumentException`.

### Phase 2: Do You Have Any Recommendations?

With the `FollowEdgeDao` ready to go, we can now build our social network! Social networks are difficult to start up.
You want people in your network to interact with each other, but if there's no one to interact with, people won't be there.
It's a chicken-or-the-egg scenario, so we need to help it grow.

We've decided to build a simple recommendation engine to help build our social network. We will create recommendations 
of other members someone could follow and load them into our database so they can be retrieved quickly. The process for
creating our recommendations will be quite simple. We're going to suggest any member that is two degrees away 
from another member. For example, if Alice follows with Bob, and Bob follows with Carly, but Alice is not
following Carly, then we recommend Carly to Alice.

To help get your mind get around this problem, consider the following questions:
1. What type of search should we perform with our recommendation engine: Depth-first or breadth-first?
2. How many DynamoDB data calls would you need to make to get recommendations based on the description above?
3. Are there any apparent and low-hanging (i.e. simple to resolve) performance issues you may want to consider now?

GOAL: implement `CreateRecommendationsActivity.handleRequest` meeting the following criteria:
1. Your solution should return a list of the recommendations created.
2. Since this method can increase in time as more people join the network, the number of recommendations created should be limited to what the input specifies.
3. Recommendations should not include anyone the member already follows or themselves. 
4. Recommendations should be unique in a given call.

### Extensions

#### Extension 1: The Unfollow
We didn't add the capability to "unfollow" someone. Add that logic to the FollowEdgeDao. Bonus points if you can add 
this functionality to the CLI.

#### Extension 2: There's Some Weight to This
GOAL: Add a weight to each `FollowEdge` that `CreateRecommendationsActivity` can use to find more likely recommendations
sooner:

1. To start, assign a random weight between 0 and 100 (higher is stronger). Update your recommendation code accordingly.
1. Weights should change over time. Determine a way to update the weight based on if recommendations are selected or not.

#### Extension 3: Storing a Graph Locally
So far, we've discussed how a graph may be stored using an association class in our database. What if we didn't have a
database and all the data was in our code? How could you store and manage a graph using simple Java collections?
