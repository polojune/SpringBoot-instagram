package com.cos.instagram.domain.notice;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.instagram.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Notice {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @Enumerated(EnumType.STRING)
        private NoticeType noticeType;
        
        @ManyToOne
        @JoinColumn(name="fromUserId")
        private User fromUser;
        
        @ManyToOne 
        @JoinColumn(name="toUserId")
        private User toUser;
        
        @CreationTimestamp 
        private Timestamp createDate;
}
