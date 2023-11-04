package com.cs203.TicketWarrior.Registration.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cs203.TicketWarrior.Registration.models.Purchase;
import com.cs203.TicketWarrior.Registration.repository.PurchaseRepository;
import com.cs203.TicketWarrior.Registration.servicesimpl.PurchaseServiceimpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private PurchaseServiceimpl purchaseService;

    @Test
    void listPurchases_ReturnEmptyList() {
        // Arrange
        when(purchaseRepository.findAll()).thenReturn(new ArrayList<Purchase>());

        // Act
        List<Purchase> returnedList = purchaseService.listPurchases();

        // Assert
        assertEquals(new ArrayList<Purchase>(), returnedList);
        verify(purchaseRepository, times(1)).findAll();
    }

    @Test
    void listPurchases_ReturnPopulatedList() {
        // Arrange
        List<Purchase> populatedList = new ArrayList<Purchase>();
        Purchase purchase1 = new Purchase("username1", "movieId1", "seatIDs1");
        Purchase purchase2 = new Purchase("username2", "movieId2", "seatIDs2");
        populatedList.add(purchase1);
        populatedList.add(purchase2);
        when(purchaseRepository.findAll()).thenReturn(populatedList);

        // Act
        purchaseService.addPurchase(purchase1);
        purchaseService.addPurchase(purchase2);
        List<Purchase> returnedList = purchaseService.listPurchases();

        // Assert
        assertEquals(populatedList, returnedList);
        verify(purchaseRepository, times(1)).findAll();
    }

    @Test
    void getPurchase_NotFound_ReturnNull() {
        // Arrange
        String purchaseId = "purchaseId";
        String userId = "userId";
        Purchase purchase = new Purchase(purchaseId, userId, "movieId", "seatIDs", "timestamp", "location", "timing","movieDate",
                0);
        String falsePurchaseID = "falsePurchaseID";
        when(purchaseRepository.findByIdAndUserId(falsePurchaseID, userId)).thenReturn(Optional.empty());

        // Act
        purchaseService.addPurchase(purchase);
        Purchase returnedPurchase = purchaseService.getPurchase(falsePurchaseID, userId);

        // Assert
        assertNull(returnedPurchase);
        verify(purchaseRepository, times(1)).findByIdAndUserId(falsePurchaseID, userId);
    }

    @Test
    void getPurchase_FoundPurchase_ReturnPurchase() {
        // Arrange
        String purchaseId = "purchaseId";
        String userId = "userId";
        Purchase purchase = new Purchase(purchaseId, userId, "movieId", "seatIDs", "timestamp", "location", "timing","movieDate",
                0);
        when(purchaseRepository.findByIdAndUserId(purchaseId, userId)).thenReturn(Optional.of(purchase));

        // Act
        purchaseService.addPurchase(purchase);
        Purchase returnedPurchase = purchaseService.getPurchase(purchaseId, userId);

        // Assert
        assertEquals(purchase, returnedPurchase);
        verify(purchaseRepository, times(1)).findByIdAndUserId(purchaseId, userId);
    }

    @Test
    void getPurchasesByUser_NotFound_ReturnEmptyList() {
        // Arrange
        String purchaseId1 = "purchaseId1";
        String userId = "userId";
        Purchase purchase1 = new Purchase(purchaseId1, userId, "movieId1", "seatIDs1",
                "timestamp1", "location1", "timing1", "movieDate", 0);

        String purchaseId2 = "purchaseId2";
        Purchase purchase2 = new Purchase(purchaseId2, userId, "movieId2", "seatIDs2",
                "timestamp2", "location2", "timing2", "movieDate", 0);

        List<Purchase> listOfPurchases = new ArrayList<Purchase>();
        listOfPurchases.add(purchase1);
        listOfPurchases.add(purchase2);

        String falseUserID = "falseUserID";

        when(purchaseRepository.findByUserId(falseUserID)).thenReturn(new ArrayList<Purchase>());

        // Act
        purchaseService.addPurchase(purchase1);
        purchaseService.addPurchase(purchase2);
        List<Purchase> returnedPurchases = purchaseService.getPurchasesByUser(falseUserID);

        // Assert
        assertEquals(new ArrayList<Purchase>(), returnedPurchases);
        verify(purchaseRepository, times(1)).findByUserId(falseUserID);
    }

    @Test
    void getPurchasesByUser_FoundPurchases_ReturnPurchases() {
        // Arrange
        String purchaseId1 = "purchaseId1";
        String userId = "userId";
        Purchase purchase1 = new Purchase(purchaseId1, userId, "movieId1", "seatIDs1",
                "timestamp1", "location1", "timing1","movieDate", 0);

        String purchaseId2 = "purchaseId2";
        Purchase purchase2 = new Purchase(purchaseId2, userId, "movieId2", "seatIDs2",
                "timestamp2", "location2", "timing2","movieDate", 0);

        List<Purchase> listOfPurchases = new ArrayList<Purchase>();
        listOfPurchases.add(purchase1);
        listOfPurchases.add(purchase2);

        when(purchaseRepository.findByUserId(userId)).thenReturn(listOfPurchases);

        // Act
        purchaseService.addPurchase(purchase1);
        purchaseService.addPurchase(purchase2);
        List<Purchase> returnedPurchases = purchaseService.getPurchasesByUser(userId);

        // Assert
        assertEquals(listOfPurchases, returnedPurchases);
        verify(purchaseRepository, times(1)).findByUserId(userId);
    }

    @Test
    void addPurchase_Success_ReturnNewPurchase() {
        // Arrange
        String purchaseId = "purchaseId";
        String userId = "userId";
        Purchase purchase = new Purchase(purchaseId, userId, "movieId", "seatIDs",
                "timestamp", "location", "timing", "movieDate", 0);

        List<Purchase> listOfPurchases = new ArrayList<Purchase>();
        listOfPurchases.add(purchase);

        when(purchaseRepository.save(purchase)).thenReturn(purchase);

        // Act
        Purchase response = purchaseService.addPurchase(purchase);

        // Assert
        assertEquals(purchase, response);
        verify(purchaseRepository, times(1)).save(purchase);
    }

    @Test
    void deletePurchase_Success() {
        // Arrange
        String purchaseId = "purchaseId";
        String userId = "userId";
        Purchase purchase = new Purchase(purchaseId, userId, "movieId", "seatIDs",
                "timestamp", "location", "timing", "moveiDate", 0);

        List<Purchase> listOfPurchases = new ArrayList<Purchase>();
        listOfPurchases.add(purchase);
        listOfPurchases.remove(0);

        when(purchaseRepository.save(purchase)).thenReturn(purchase);
        when(purchaseRepository.findByIdAndUserId(purchaseId, userId)).thenReturn(Optional.empty());

        // Act
        purchaseService.addPurchase(purchase);
        purchaseService.deletePurchase(purchaseId);
        Purchase response = purchaseService.getPurchase(purchaseId, userId);

        // Assert
        assertNull(response);
        verify(purchaseRepository, times(1)).save(purchase);
        verify(purchaseRepository, times(1)).deleteById(purchaseId);
        verify(purchaseRepository, times(1)).findByIdAndUserId(purchaseId, userId);
    }
}