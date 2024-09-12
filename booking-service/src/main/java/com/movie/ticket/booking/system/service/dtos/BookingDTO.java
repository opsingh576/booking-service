package com.movie.ticket.booking.system.service.dtos;

import com.movie.ticket.booking.system.service.enums.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Builder
public class BookingDTO {

    private UUID bookingId;

    @NotBlank(message ="user id is mandatory")
    private String userId;

    @Positive(message="Please provide a movie id")
    @NotNull(message= "movie id is mandatory")
    private Integer movieId;

    @NotNull(message = "you need to select at least one seat to create booking")
    private List<String> seatsSelected;

    @NotNull(message = "select show date")
    private LocalDate showDate;

    @NotNull(message = "select show time")
    private LocalTime showTime;

    @Positive(message = "Booking amount must be a positive value")
    private Double bookingAmount;

    private BookingStatus bookingStatus;

}
