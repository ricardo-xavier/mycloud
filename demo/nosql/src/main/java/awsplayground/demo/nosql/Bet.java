package awsplayground.demo.nosql;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Bet {
    private String id;
    private String league;
    private Integer home;
    private Integer visitors;

    @DynamoDbPartitionKey
    @DynamoDbAttribute(value = "hash")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute(value = "sort")
    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public Integer getHome() {
        return home;
    }

    public void setHome(Integer home) {
        this.home = home;
    }

    public Integer getVisitors() {
        return visitors;
    }

    public void setVisitors(Integer visitors) {
        this.visitors = visitors;
    }
}
