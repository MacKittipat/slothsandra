package mac.kittipat.slothsandra.core.model;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class YearByChannel {

    @PrimaryKey
    private YearByChannelKey yearByChannelKey;

    public YearByChannelKey getYearByChannelKey() {
        return yearByChannelKey;
    }

    public void setYearByChannelKey(YearByChannelKey yearByChannelKey) {
        this.yearByChannelKey = yearByChannelKey;
    }
}
