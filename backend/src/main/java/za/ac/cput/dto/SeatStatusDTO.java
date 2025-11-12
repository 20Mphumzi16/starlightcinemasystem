package za.ac.cput.dto;

public class SeatStatusDTO {
    private Long id;
    private String seatNumber;
    private boolean occupied;

    public SeatStatusDTO(Long id, String seatNumber, boolean occupied) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.occupied = occupied;
    }

    public Long getId() {
        return id;
    }

    public SeatStatusDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public SeatStatusDTO setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public SeatStatusDTO setOccupied(boolean occupied) {
        this.occupied = occupied;
        return this;
    }

    @Override
    public String toString() {
        return "SeatStatusDTO{" +
                "id=" + id +
                ", seatNumber='" + seatNumber + '\'' +
                ", occupied=" + occupied +
                '}';
    }
}