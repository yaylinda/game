package yay.linda.entity;

@Table
public class Session implements Serializable {
    @PrimaryKey
    private UUID sessionId;
    private UUID playerId;
}
