package com.kgn.store.bootstrap;

import com.kgn.store.model.Product;
import com.kgn.store.repo.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Seeds the catalogue on first start if it is empty. These 14 products mirror
 * the storefront. To add/change products in production, use the database or
 * extend this list and redeploy.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository products;

    public DataInitializer(ProductRepository products) {
        this.products = products;
    }

    @Override
    public void run(String... args) {
        if (products.count() > 0) return;
        products.saveAll(List.of(
            new Product(1L, "Tenax Ager Colour Enhancing Sealer 1L", "Tenax", "sealers",
                "images/tenax-ager.jpg", 2850, 3400, 4.9, 186, 25, "HOT",
                "Italian wet-look sealer · Anti-graffiti · 1 Litre · Raw-smooth natural stones"),
            new Product(2L, "Tenax Hydrex Polished Stone Sealer 1L", "Tenax", "sealers",
                "images/tenax-hydrex.jpg", 2450, 2800, 4.8, 142, 18, "",
                "Idro-oleorepellente · Water & oil repellent · For polished marble & granite"),
            new Product(3L, "Tenax Mastic Solido Paglierino Ivory", "Tenax", "adhesives",
                "images/tenax-mastic.jpg", 1850, 2200, 4.7, 98, 30, "",
                "Ivory solid mastic · To fill and glue marble & stones"),
            new Product(4L, "MYK Laticrete Clenza TC Tile Cleaner 500ml", "MYK Laticrete", "cleaners",
                "images/clenza-tc.jpg", 650, 780, 4.7, 234, 60, "HOT",
                "Active cleaning tech · Heavy duty tile cleaner · Removes tough stains"),
            new Product(5L, "MYK Laticrete Clenza TS Tap & Shower Cleaner", "MYK Laticrete", "cleaners",
                "images/clenza-ts.jpg", 520, 620, 4.6, 178, 45, "",
                "Spray bottle · Removes soap scum, hard water & limescale · Restores shine"),
            new Product(6L, "MYK Laticrete DWA 215 Waterproofing 20kg", "MYK Laticrete", "waterproof",
                "images/myk-dwa-215.jpg", 4200, 4800, 4.7, 88, 12, "B2B",
                "20 kg pack · Cement-based waterproofing · For terraces & wet areas"),
            new Product(7L, "MYK Laticrete Sealer 190 (20L)", "MYK Laticrete", "waterproof",
                "images/myk-sealer-190.jpg", 6800, 7600, 4.6, 64, 8, "B2B",
                "20 Litre bucket · Water-based · Weather resistant · Interior & exterior"),
            new Product(8L, "MYK Laticrete Latapoxy A+B Epoxy Adhesive 1.8kg", "MYK Laticrete", "adhesives",
                "images/latapoxy.jpg", 1450, 1700, 4.8, 124, 35, "NEW",
                "All purpose epoxy adhesive · Extra strong bond · 1.8 kg A+B kit"),
            new Product(9L, "Roff Cera Clean Rapid Action Tile Cleaner T16", "Roff (Pidilite)", "cleaners",
                "images/roff-cera-clean.jpg", 380, 450, 4.5, 312, 80, "",
                "Rapid action · Removes stains · Can be diluted in water · Tile & ceramic"),
            new Product(10L, "Roff Rainbow Tile Mate Wide Additive T14", "Roff (Pidilite)", "grout",
                "images/roff-rainbow.jpg", 340, 400, 4.6, 198, 90, "",
                "Latex-based tile grout additive · Higher flexibility · Water-resistant grout"),
            new Product(11L, "Araldite Standard Epoxy Resin + Hardener Kit", "Araldite", "adhesives",
                "images/araldite.webp", 285, 340, 4.9, 892, 200, "HOT",
                "AW 106 IN Resin + HV 953 IN Hardener · Highest bond · Multi substrate · Waterproof"),
            new Product(12L, "Astral Vetra LV 401 Instant Adhesive", "Astral Adhesives", "adhesives",
                "images/vetra-lv401.jpg", 240, 290, 4.5, 156, 120, "",
                "Instant cyanoacrylate adhesive · Low viscosity · For glass, ceramic, metal"),
            new Product(13L, "Grip Stone Rough Coat for Better Grip", "Grip Stone", "polish",
                "images/grip-stone.png", 1850, 2100, 4.6, 84, 22, "",
                "ISO 9001:2015 · For marble, granite, stone adhesion · Single coat · 24h drying"),
            new Product(14L, "Tenax Proseal Water & Oil Repellent 1L", "Tenax", "sealers",
                "images/tenax-hydrex.jpg", 2650, 3100, 4.7, 76, 15, "NEW",
                "Idro-oleorepellente · For polished granite · Italian quality")
        ));
    }
}
