package com.movie.ticket.booking.system.service.services.impl;

import com.movie.ticket.booking.system.service.brokers.PaymentServiceBroker;
import com.movie.ticket.booking.system.service.dtos.BookingDTO;
import com.movie.ticket.booking.system.service.dtos.ResponseDTO;
import com.movie.ticket.booking.system.service.entities.BookingEntity;
import com.movie.ticket.booking.system.service.enums.BookingStatus;
import com.movie.ticket.booking.system.service.repositories.BookingRepository;
import com.movie.ticket.booking.system.service.services.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    //private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentServiceBroker paymentService;

    @Override
    public ResponseDTO createBooking(BookingDTO bookingDTO) {
        //log.info("Entered into BookingServiceImpl createBooking method with request data : {}", bookingDTO);

       /* BookingEntity bookingEntity1 = new BookingEntity();

        bookingEntity1.setMovieId(bookingDTO.getMovieId());
        bookingEntity1.setBookingAmount(bookingDTO.getBookingAmount());
        bookingEntity1.setBookingStatus(BookingStatus.PENDING);
        bookingEntity1.setShowDate(bookingDTO.getShowDate());
        bookingEntity1.setShowTime(bookingDTO.getShowTime());
        bookingEntity1.setSeatsSelected(bookingDTO.getSeatsSelected());
        this.bookingRepository.save(bookingEntity1); */

        //with Builder

        BookingEntity bookingEntity=BookingEntity.builder()
                .userID(bookingDTO.getUserId())
                .movieId(bookingDTO.getMovieId())
                .bookingAmount(bookingDTO.getBookingAmount())
                .bookingStatus(BookingStatus.PENDING)
                .showDate(bookingDTO.getShowDate())
                .showTime(bookingDTO.getShowTime())
                .seatsSelected(bookingDTO.getSeatsSelected())
                .build();

        this.bookingRepository.save(bookingEntity);

        //call payment service

        String paymentResponse=this.paymentService.createPayment();
      return  ResponseDTO.builder()
                .bookingDTO(BookingDTO.builder()
                        .bookingId(bookingEntity.getBookingID())
                        .userId(bookingEntity.getUserID())
                        .movieId(bookingEntity.getMovieId())
                        .bookingAmount(bookingEntity.getBookingAmount())
                        .bookingStatus(bookingEntity.getBookingStatus())
                        .showDate(bookingEntity.getShowDate())
                        .showTime(bookingEntity.getShowTime())
                        .seatsSelected(bookingEntity.getSeatsSelected())
                        .build())
              .build();


    }
}