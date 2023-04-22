package br.com.ada.figurinhas.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Entity
@Table(name = "figurinha")
@NoArgsConstructor
@AllArgsConstructor
public class Figurinha {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @JoinColumn(name = "figurinhaPrototipoIdFk", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private FigurinhaPrototipo figurinhaPrototipo;

    @Column(name = "albumId", nullable = false)
    private String albumId;

    @OneToMany(mappedBy = "figurinha")
    private List<FigurinhaJournal> stickJournal;

    @OneToOne(mappedBy = "figurinha")
    private FigurinhaToSell figurinhaToSell;
}
