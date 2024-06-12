package com.przemyslawren.escapethat.repository;

import com.przemyslawren.escapethat.model.Booking;
import com.przemyslawren.escapethat.model.EscapeRoom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT MAX(b.slotNumber) FROM Booking b WHERE b.escapeRoom = :escapeRoom")
    Optional<Integer> findMaxSlotNumberByEscapeRoom(EscapeRoom escapeRoom);

    @Query("SELECT b.slotNumber FROM Booking b WHERE b.escapeRoom = :escapeRoom AND b.startTime = :startTime")
    Optional<Integer> findSlotNumberByEscapeRoomAndStartTime(EscapeRoom escapeRoom, LocalDateTime startTime);

    boolean existsByEscapeRoomAndStartTime(EscapeRoom escapeRoom, LocalDateTime startTime);
    Optional<Booking> findByEscapeRoomIdAndSlotNumberAndStartTime(Long escapeRoomId, int slotNumber, LocalDateTime startTime);
    @Query("SELECT b FROM Booking b WHERE b.escapeRoom.id = :escapeRoomId AND b.slotNumber = :slotNumber AND b.startTime BETWEEN :startTimeStart AND :startTimeEnd")
    List<Booking> findBookingsBySlotNumberAndDateRange(Long escapeRoomId, int slotNumber, LocalDateTime startTimeStart, LocalDateTime startTimeEnd);
}
